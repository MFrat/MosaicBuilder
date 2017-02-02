# MosaicBuilder

Mosaic made of square image tiles. Each pixel of the input image is replaced by a tile.

##Samples
Using 50x50 image tiles and ImagePack tiles library.

Guerrero, biggest brazillian team's striker. 
<p align="center">
  <img src="http://www.sportv4.com/wp-content/uploads/2016/03/1158.jpg" width="400"/>
  <img src="http://i.imgur.com/uRk5bga.jpg" width="400"/>
</p>

DayZ's most famous image.
<p align="center">
  <img src="http://i.imgur.com/u73kIO9.jpg" width="400"/>
  <img src="http://i.imgur.com/v2crLWJ.png" width="400"/>
</p>

Computer Science Institute - UFF
<p align="center">
  <img src="http://i.imgur.com/cvmxYvL.jpg" width="400"/>
  <img src="http://i.imgur.com/d8lBZ7V.jpg" width="400"/>
</p>

# Usage

        ```String tilePath = "D:\\SomeFolder\\my_pack_50x50";
         String inputImagePath = "D:\\SomeFolder\\inputImage.jpg";
         final String finalPath = "D:\\SomeFolder\\outPutImage.png";
         int tileDimension = 50;
        
        MosaicBuilder mosaicBuilder = new MosaicBuilder(tilePath, inputImagePath, tileDimension);

        mosaicBuilder.setListener(new MosaicBuilder.MosaicBuilderListener() {
           @Override
           public void onMosaicFinished(BufferedImage img) {
               try {
                   ImageIO.write(img, "png", new File(finalPath));
               } catch (IOException ex) {
                   //TODO
                   //Warn user, etc...
               }
           }

           @Override
           public void onProgressChanged(int status) {
               switch (status){
                    case MosaicBuilder.BUILDING_OUTPUT_IMAGE:
                        System.out.println("Building final image...");
                        break;
                     case MosaicBuilder.READING_INPUT_IMAGE_INTO_MEMORY:
                        System.out.println("Reading input image into memory...");
                        break;
                     case MosaicBuilder.READING_TILES_INTO_MEMORY:
                        System.out.println("Reading tiles into memory...");
                        break;
                     case MosaicBuilder.SELECTING_TILES:
                        System.out.println("Selecting best tiles for each input image's pixel...");
                        break;
               }
           }
       });
        
        mosaicBuilder.build();```
