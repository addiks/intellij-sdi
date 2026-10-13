## IntelliJ Plugin - Single Document Interface

** WORK IN PROGRESS! This plugin is still in the very beginning of its development! **

The purpose of this plugin is to turn IntelliJ from an [MDI][mdi] (Multiple Document Interface, everything in one big 
window) into an [SDI][sdi] (Single Document Interface), every Document in its own window.

[mdi]: https://en.wikipedia.org/wiki/Multiple-document_interface
[sdi]: https://en.wikipedia.org/wiki/Multiple-document_interface#Comparison_with_single-document_interface

### Installation
Since this plugin uses internal IntelliJ Platform API's, it is not allowed to be listed in the official marketplace.
Because of that, I have set up my own custom repository on [my homepage][homepage].
You can add this custom repository (Point it to the [`updatePlugins.xml`][update-plguins-xml] file) and install this
plugin from there.

Alternatively you can also build your own plugin-jar by cloning this repo in IntelliJ and using
the Gradle task `intellij platform > composedJar` which will then create the plugin jar file under 
`build/libs/intellij-sdi-*.jar`. This file can then be installed in the plugin window by clicking on the cogwheel and 
then using the `Install plugin from disk` option.

Once installed you can enable, disable and configure the features under: 
    `Settings` > `Appearance & Behaviour` > `Single Document Interface (SDI)`.

[homepage]: https://www.addiks.de/repo/intellij-plugins/
[update-plguins-xml]: https://www.addiks.de/repo/intellij-plugins/updatePlugins.xml

### Implemented Feaures:
* Move every Tool-Window into its own window
* Move every Editor-Window into its own window
* Determine and apply a usable window-size for newly opened windows
* Configuration-options
* Close **all** detached windows (Under "Window" > "Close All")

### TODO:
* Close **old** detached windows
* Shortcut to move from a detached window directly to the main window
* Tests
* Documentation
* Probably more