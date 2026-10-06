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

Once installed, you can enable (or disable) the features under: 
    `Settings` > `Appearance & Behaviour` > `Single Document Interface (SDI)`.

[homepage]: https://www.addiks.de/repo/intellij-plugins/
[update-plguins-xml]: https://www.addiks.de/repo/intellij-plugins/updatePlugins.xml

### Implemented Feaures:
* Move every Tool-Window into it's own window
* Move every Editor-Window into it's own window
* Configuration-options
* Close **all** detached windows (Under "Window" > "Close All")

### TODO:
* Determine and apply a usable window-size for newly opened windows
* Close **old** detached windows
* Shortcut to move from a detached window directy to the main window
* Tests
* Documentation
* Probably more