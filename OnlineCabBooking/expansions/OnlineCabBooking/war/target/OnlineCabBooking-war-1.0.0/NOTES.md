# Web-Styles

## Minified files

Since web-styles version 2021.1.0, js files under `struts/js/nsx` will be minified to remove TODO's 
and improve performance of the build and at runtime.

This means, however, that the files will no longer be human-readable.
Should you wish to consult the original js files, you can add the application instance option `webstyles.patch.unminify`.
The expanders will then copy the original files instead.