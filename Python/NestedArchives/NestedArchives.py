# pip install patool

import os

# import patoolib or install it if it isn't installed
try:
    import patoolib
except ImportError:
    print("Trying to Install required module")
    os.system("python -m pip install patool")
import patoolib

def unpack_all(path_to):
    print("Unpacking")
    for root, dirs, files in os.walk(path_to):
        os.chdir(root)
        for file in files:
            fname = os.path.join(root, file)
            patoolib.extract_archive(fname)
    print("Finished")

def create_nested_archive(path_to):
    os.chdir(path_to.rpartition("\\")[0])
    fname = path_to
    count = int(input("How many times you want to archive (nested archive): "))
    extension = "." + \
        str(input("Which archive file extension do you want to use: "))

    print("Creating nested archive")

    tmp_name = fname.split("\\")[-1].split(".")[0] + "-" + str(count).zfill(3)
    patoolib.create_archive(tmp_name + extension, (fname.split("\\")[-1], ))
    while count > 1:
        output_name = tmp_name + extension
        count -= 1
        tmp_name = fname.split(
            "\\")[-1].split(".")[0] + "-" + str(count).zfill(3)
        patoolib.create_archive(tmp_name + extension, (output_name, ))
        os.remove(output_name)

    print("Finished")

def unpack_nested_archive(path_to):
    os.chdir(path_to.rpartition("\\")[0])
    fname = path_to
    dir_name = fname.split("\\")[-1].split("-")[0]
    os.mkdir(dir_name)

    print("Unpacking nested archive")

    patoolib.extract_archive(fname, outdir=dir_name)
    os.remove(fname)
    os.chdir(dir_name)
    new_path = os.getcwd()
    while True:
        for root, dir, file in os.walk(new_path):
            try:
                fname = file[0]
                # file or directory
                if fname.split(".")[1] in patoolib.ArchiveFormats:
                    patoolib.extract_archive(fname)
                    os.remove(fname)
                else:
                    print("Finished")
                    return False
            except IndexError:
                print("Finished")
                return False

print("Script for creating and unpacking nested archives")
print("\t1) Create nested archive",
      "\t2) Unpack nested archive",
      "\t3) Unpack archives in folders and subfolders",
      "\t4) exit", 
      sep="\n")

choice = int(input("Please select what you want to do: "))

match choice:
    case 1:
        path_to = input("Enter the path to the directory or files: ")
        create_nested_archive(path_to)
    case 2:
        path_to = input("Enter the path to the directory or files: ")
        unpack_nested_archive(path_to)
    case 3:
        path_to = input("Enter the path to the directory or files: ")
        unpack_all(path_to)
    case 4:
        exit