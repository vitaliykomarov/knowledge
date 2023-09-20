# Python

For the [weather](https://github.com/vitaliykomarov/knowledge/blob/main/Python/Weather/weather.py) script install the requests library
```
python -m pip install requests
```

[Nested archives script](https://github.com/vitaliykomarov/knowledge/blob/main/Python/NestedArchives/NestedArchives.py) uses patool, it should be installed by the following command:
```
python -m pip install patool
```
<details>
  <summary>Nested archives script</summary>
  
  This scrip can also install patool if it doesn't exist.
  <br>How the script works:
  <br>1\) Create nested archive
  file(s) or folder(s) will be packed into FILE_FOLDER_NAME-%%%.{EXTENSION}
  %%% - number will show how many times it'll be packed
  {EXTENSION} - archive file extension
  Example:
  ```
  some_file.txt
  └── some_file-002.zip
      └── some_file-001.zip
  In the end you'll get some_file-001.zip 
  ```
  2\) Unpack nested archive
  Choose nested archive and it'll be unpacked into a new folder
  Example:
  ```
  some_file-001.zip
  └── some_file
      └── some_file.txt
  ```

  3\) Unpack archives in folders and subfolders
  Example
  ```
  # Before
  folder1
  └── archive1.zip
  folder2
  └── archive2.zip

  # After
  folder1
  ├── archive1.zip
  └── archive2
  folder2
  ├── archive2.zip
  └── archive2
  ```
  
</details>
