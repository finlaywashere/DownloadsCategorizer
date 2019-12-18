# Using the Downloads Categorizer

## 1.0 Setup

### 1.1 Configuration files

The Downloads Categorizer requires 1 configuration file in the Downloads folder named `.dcconfig`

This configuration file is in the format `(glob pattern) folder`

Note: There cannot be any spaces in the glob pattern, however there can be spaces in the folder path

Also note: The folder path is relative to the Downloads folder

### 1.2 Installation

The Downloads Categorizer has no installer, and as such it must be installed manually.

To do this simply set up a batch/shellscript file to run the daemon on startup.

Note: Passing any arguments to the daemon will disable the easter egg

## 2.0 Usage

### 2.1 Categorizer Usage

The categorizer daemon runs in the background, and as such requires no user interaction

### 2.2 File Searching Utility

The categorizer comes with a built in indexed file searching utility for the Downloads folder, this can be used by running the utility jar or the Main class.
