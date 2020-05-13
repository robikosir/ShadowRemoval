image = imread('3.png');

img_R = double(image(:,:,1));
img_G = double(image(:,:,2));
img_B = double(image(:,:,3));

%green gradient
[Gx,Gy] = imgradientxy(img_G,'intermediate');
[gmag,~] = imgradient(Gx,Gy);
greenX = imfilter(img_G, [0 0 0; 0 -1 1; 0 0 0], 'replicate');
greenY = imfilter(img_G, [0 0 0; 0 -1 0; 0 1 0], 'replicate');
greenMagnitude = gmag;

%red gradient
[Gx,Gy] = imgradientxy(img_R,'intermediate');
[gmagR,~] = imgradient(Gx,Gy);
redX = imfilter(img_R, [0 0 0; 0 -1 1; 0 0 0], 'replicate');
redY = imfilter(img_R, [0 0 0; 0 -1 0; 0 1 0], 'replicate');
redMagnitude = gmagR;

%blue gradient
[Gx,Gy] = imgradientxy(img_B,'intermediate');
[gmag,~] = imgradient(Gx,Gy);
blueX = imfilter(img_B, [0 0 0; 0 -1 1; 0 0 0], 'replicate');
blueY = imfilter(img_B, [0 0 0; 0 -1 0; 0 1 0], 'replicate');
blueMagnitude = gmag;

%invariant image
image = image + 1;
[h, w, d] = size(image);

img_R = img_R / 255;
img_G = img_G / 255;
img_B = img_B / 255;

geoM = (img_R .* img_G .* img_B).^(1/3);

ch_r = log(img_R ./ geoM);
ch_b = log(img_B ./ geoM);


%rad_t = pi * (alpha-1) / 180;
grayImg = ch_r * cos(0.8) + ch_b * sin(0.8);
     
 tmp = grayImg;
 tmp = tmp - min(min(tmp));
 tmp = tmp / max(max(tmp));
 
[GxI,GyI] = imgradientxy(tmp);
[gmagI,gdirI] = imgradient(GxI,GyI);
%invariant image end


%function S
[height,width] = size(gmag);
for x = 1 : height
  for y = 1 : width
      if greenMagnitude(x,y) > 5 && gmagI(x,y) < 0.1
           greenX(x,y) = 0;
           greenY(x,y) = 0;
      end
      if redMagnitude(x,y)> 5 && gmagI(x,y) < 0.1
           redX(x,y) = 0;
           redY(x,y) = 0;
      end
      if blueMagnitude(x,y)> 5 && gmagI(x,y) < 0.1
           blueX(x,y) = 0;
           blueY(x,y) = 0;
      end
  end
end

%calculate laplacian from gradient
blueXX = imfilter(blueX, [0 0 0; -1 1 0; 0 0 0], 'replicate');
blueYY = imfilter(blueY, [0 -1 0; 0 1 0; 0 0 0], 'replicate');

redXX = imfilter(redX, [0 0 0; -1 1 0; 0 0 0], 'replicate');
redYY = imfilter(redY, [0 -1 0; 0 1 0; 0 0 0], 'replicate');

greenXX = imfilter(greenX, [0 0 0; -1 1 0; 0 0 0], 'replicate');
greenYY = imfilter(greenY, [0 -1 0; 0 1 0; 0 0 0], 'replicate');

laplacR = redXX + redYY;
laplacG = greenXX + greenYY;
laplacB = blueXX + blueYY;


%slove laplacian equation
r = matrixSolving(laplacR, width, height);
g = matrixSolving(laplacG, width, height);
b = matrixSolving(laplacB, width, height);

%convert to 0-255
red = uint8(255 * mat2gray(r));
green = uint8(255 * mat2gray(g));
blue = uint8(255 * mat2gray(b));

%merge channels
rgbImage = cat(3, red,green,blue);
imshow(rgbImage,[]);
imwrite(rgbImage, 'shadowFree.png');


% red(red>meanValues(1)) = 111;
% green(green>meanValues(2)) = 111;
% blue(blue>meanValues(3)) = 111;

 
% rgbImage = cat(3, red,green,blue);

function laplacOfChannel = matrixSolving(fun, width, height)
    nx = width;
    ny = height;
    diag_mat = eye(ny) * (-4);
    diag_mat = diag_mat + diag(ones(ny-1,1),1);
    diag_mat = diag_mat + diag(ones(ny-1,1),-1);
    a_off = eye(ny);
    A = sparse((ny)*(nx),(ny)*(nx));


    for i = 1:nx
        A((i-1)*(ny)+1:(i-1)*(ny) + (ny),(i-1)*(ny)+1:(i-1)*(ny) + (ny)) = diag_mat;
    end
    % 
     for i = 2:nx
         A((i-2)*(ny)+1:(i-2)*(ny) + (ny),(i-1)*(ny)+1:(i-1)*(ny) + (ny)) = a_off;
         A((i-1)*(ny)+1:(i-1)*(ny) + (ny),(i-2)*(ny)+1:(i-2)*(ny) + (ny)) = a_off;
     end
     functionF = fun(:);
     laplacOfChannel = A \ functionF;
     laplacOfChannel = reshape (laplacOfChannel, ny, []);
end

    function processedResult = laplacian(inputImage, direction)
%         inputImage = conv2(inputImage,[0,0,0;0,1,0;0,0,0]);
%         inputImage = [inputImage(:,end) inputImage inputImage(:,1)]; 
%         inputImage = [inputImage(end,:); inputImage; inputImage(1,:)]; 
        N   = size(inputImage);
        % do (de-)convolution in Fourier domain: 
        FT_original = fftn(inputImage); 
        % create forward Laplacian filter: 
        [lap_x,lap_y] = meshgrid(...
            2 - 2*cos(2*pi*(0:N(2)-1)/N(2)),...
            2 - 2*cos(2*pi*(0:N(1)-1)/N(1)));
        if direction == 1
            LapFilter = lap_x + lap_y;
        else
            LapFilter = 1./(lap_x + lap_y);
            LapFilter(1,1) = 0;
        end
        % (de)convolve in Fourier domain: 
        FT_output = FT_original.*LapFilter;
        % go back to image domain: 
        processedResult = real(ifftn(FT_output)); 
    end
    
    function output = findMeanOfMax(red,green,blue)
        %calculate top 1 percentile of every single channel
        redTop = prctile(red,99,'all');
        greenTop = prctile(green,99,'all');
        blueTop = prctile(green,99,'all');

        %calculate mean values of percentiles
        tresholdedRed = red > redTop;
        tresholdedBlue = green > greenTop;
        tresholdedGreen = blue > blueTop;

        redMean = mean(reshape(red(tresholdedRed),1,[]));
        blueMean = mean(reshape(green(tresholdedBlue),1,[]));
        greenMean = mean(reshape(blue(tresholdedGreen),1,[]));
        
        output = [redMean, greenMean, blueMean];
      end