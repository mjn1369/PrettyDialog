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
PrettyDialog extends Dialog class, so feel free to use its inherited functions.
**Note:** Default dialog has no title, message or any buttons. Just a close icon on top which you can dismiss the dialog by clicking on it.
### Simple Dialog, No Customization:
```
new PrettyDialog(this)
	.setTitle("PrettyDialog Title")
	.setMessage("PrettyDialog Message")
	.show();
```
##### Output:
![alt text](https://github.com/mjn1369/PrettyDialog/blob/master/Screenshots/1.png "Simple Dialog, No Customization")
### Change Icon:
- You can set the dialog icon resource:
```
	...
	.setIcon(R.drawable.pdlg_icon_info)
	...
```
- And set a color tint for it:
```
	...
	.setIconTint(R.color.pdlg_color_green)
	...
```
- Ultimately, define an OnClick callback:
```
	...
	.setIconCallback(new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        // Do what you gotta do
                    }
                })
	...
```
- Put them all together:
```
	...
	.setIcon(
		R.drawable.pdlg_icon_info,    // icon resource
		R.color.pdlg_color_green,      // icon tint
		new PrettyDialogCallback() {  // icon OnClick listener
		    @Override
		    public void onClick() {
			// Do what you gotta do
		    }
                })
```
##### Output:
![alt text](https://github.com/mjn1369/PrettyDialog/blob/master/Screenshots/2.png "Customize icon")
