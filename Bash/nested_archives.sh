#!/bin/bash

echo "This script can create and unpack nested archives (tar.gz)"
options=(
"Unpack nested archive"
"Create nested archive"
"Exit")
PS3='Please select what you want to do: '
select opt in "${options[@]}"
do
	case $opt in
		"Unpack nested archive")
			echo "You chose unpack nested archive"
			echo "Unpacking"
			while [ -f *.tar.gz ]; do
				find . -depth -name '*.tar.gz' -exec tar zxfv {} \; -delete;
			done
			echo "Finished"
			exit 1
			;;
		"Create nested archive")
			echo "You chose create nested archive"
			read -e -p "Please enter the file you want to archive: " fname
			read -p "How many times do you want to archive (nested archive): " count
			tname=${fname%%.*}-$(printf %03d $count)
			tar -czf $tname.tar.gz $fname

			echo "Archiving..."

			while [[ $count > 1 ]]
			do
				oname=$tname.tar.gz
				let "count=count-1"
				tname=${fname%%.*}-$(printf %03d $count)
				tar -czf $tname.tar.gz $oname
				rm $oname
			done

			echo "Finished!"
			echo "Nested archive created - $tname.tar.gz"
			exit 1
			;;
		"Exit")
			exit 1
			;;
	esac
done
