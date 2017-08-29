# PrettyDialog
PrettyDialog is a customizable equivalent of SCLAlertView in iOS.

Example is available in app module.
## Download
### Gradle:
Add the following to your project level build.gradle:

```
allprojects {
 repositories {
  ...
  maven { url "https://jitpack.io" }
 }
}
```
Add this to your app build.gradle:

```
dependencies {
 compile 'com.github.mjn1369:prettydialog:1.0.0'
}
```
## Usage
PrettyDialog extends Dialog class, so feel free to use inherited functions.
### Simple dialog, nothing fancy:
```
new PrettyDialog(this)
	.setTitle("Simple Title")
	.setMessage("Simple message")
	.show();
```
####Output:
