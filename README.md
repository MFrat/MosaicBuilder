# MosaicBuilder

Mosaic made of square image tiles. Each pixel of the input image is replaced by a tile.

Library under development!

Comming soon:
- Full multi-thread processing.
- DeltaE color difference algorithm.
- Better tile placement algorithm, to avoid sequence repetition.
- Graphic interface implementation.

### Packs

[MegaPack.zip](https://mega.nz/#!XpsWDKpa!5P96H4Wd_7unRwlrfg0izY0eXIpAVMXnWpXl56ePB3I) (over 12.000 50x50 tiles).

[DayZPack.zip](https://mega.nz/#!LxEEBbiD!wxj0u9Xo_VXU9mjj1Ut0ft89gJW-VgSfIMExCbzanCs) (over 1.200 50x50 dayz-themed tiles).

## Samples(Using MegaPack)

**Guerrero, biggest brazillian team's striker.**
<p align="center">
  <img src="http://www.sportv4.com/wp-content/uploads/2016/03/1158.jpg" width="400"/>
  <img src="http://i.imgur.com/uRk5bga.jpg" width="400"/>
</p>

**DayZ's most famous image.**
<p align="center">
  <img src="http://i.imgur.com/u73kIO9.jpg" width="400"/>
  <img src="http://i.imgur.com/v2crLWJ.png" width="400"/>
</p>

**Computer Science Institute - UFF**
<p align="center">
  <img src="http://i.imgur.com/cvmxYvL.jpg" width="400"/>
  <img src="http://i.imgur.com/d8lBZ7V.jpg" width="400"/>
</p>

# Usage

##Constructor
```java
MosaicBuilder mosaicBuilder = new MosaicBuilder(tilePath, inputImagePath, tileDimension);
```
```tilePath``` folder containing tiles.

```inputImagePath``` path of the inputImage.

```tileDimen``` Dimension of the tile. (e.g 50x50, 60x60, 40x40, ...).


##Listener(Callback interface)
```java
public interface MosaicBuilderListener{
      public void onMosaicFinished(BufferedImage img);
      public void onProgressChanged(int status);
}
```

`onMosaicFinished(BufferedImage img);` is called when final image is done.

`onProgressChanged(int status);` is called when the progress changes (e.g, init -> loading inputImage to memory).


## Progress Status static constants
```java
public static final int READING_INPUT_IMAGE_INTO_MEMORY;
public static final int READING_TILES_INTO_MEMORY;
public static final int SELECTING_TILES;
public static final int BUILDING_OUTPUT_IMAGE;
```

## Example
```java
String tilePath = "D:\\SomeFolder\\my_pack_50x50";
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

mosaicBuilder.build();
```

## Developed by

**Max Fratane**

[Facebook](https://www.facebook.com/max.fratane)
