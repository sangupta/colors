# avu

Strongly-typed immutable color-models and utility conversion functions in Java. 

## Usage examples

```java
float[] hsl = new RGB("#a4b52f").invert().hsl().asArray();

// various ways to created colors
CMY cmy = new RGB("#abc").cmy();
CMYK cmyk = new RGB(29, 39, 49).cmyk();

// multiple conversions
HSL = new HSB(0.3f, 0.3f, 0.3f).rgb().hsi().rgb().hsl();
```

## Available Color Models

* CMY - Cyan, Magenta, Yellow
* CMYK - Cyan, Magenta, Yellow, Black
* HSB - Hue, Saturation, Brightness
* HSI - Hue, Saturation, Intensity
* HSL - Hue, Saturation, Luminosity
* HunterLAB
* LAB
* RGB - Red, Green, Blue
* RGBA - Red, Green, Blue, Alpha
* XYZ - CIE 1931 XYZ color space including following Illuminants: A, C, D50, D55, D65, D75, F2, F7, F11
* YIQ
* YUV
* Yxy

## Available Conversions

* CMY <> RGB
* CMYK <> RGB
* HSB <> RGB
* HSI <> RGB
* HSL <> RGB
* HunterLAB <> XYZ
* LAB <>
* RGB (base)
* RGBA
* XYZ <> RGB
* YIQ <> RGB
* YUV <> RGB
* Yxy <> XYZ

## RoadMap

* Add YCbCr family of color models - https://en.wikipedia.org/wiki/YCbCr
* CIELUV color model - https://en.wikipedia.org/wiki/CIELUV
* CIE-Lch color model
* Test cases

## Versioning

For transparency and insight into our release cycle, and for striving to maintain backward 
compatibility, `colors` will be  maintained under the Semantic Versioning guidelines 
as much as possible.

Releases will be numbered with the follow format:

```
<major>.<minor>.<patch>
```

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

## References

* http://colormine.org/colors-by-name
* https://en.wikipedia.org/wiki/CIE_1931_color_space
* https://en.wikipedia.org/wiki/Lab_color_space
* http://www.easyrgb.com/index.php?X=MATH&H=16
* https://en.wikipedia.org/wiki/Color_temperature


## License

```
avu - Strongly typed immutable color models
Copyright (c) 2017, Sandeep Gupta

https://sangupta.com/projects/avu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
