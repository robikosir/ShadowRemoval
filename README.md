# Shadow removal
Remove shadows from image based on this [paper](https://dl.acm.org/doi/10.5555/645318.649239)

Input image:<br />
<img src="https://user-images.githubusercontent.com/11437555/81850212-fac30680-9557-11ea-9818-2b71b620341a.png" width="200" height="300">
<img src="https://user-images.githubusercontent.com/11437555/81921639-35ba4e00-95db-11ea-9e90-b84070bb966d.jpg" width="641" height="300">


Output image:<br />
<img src="https://user-images.githubusercontent.com/11437555/81850839-d0257d80-9558-11ea-91ae-9ff8c983e7a4.png" width="200" height="300">
<img src="https://user-images.githubusercontent.com/11437555/81921856-8af65f80-95db-11ea-8485-30f13f969768.png" width="641" height="300">

The paper states: 
"One simple approach to determining the true edges is to threshold the edge
maps such that weak edges are set to zero. We found however that this still does
not produce edge maps which are clean enough for our purposes.". In order to improve removing of shadows, additional shadow edge detection has to be implemented.
