#!/bin/sh

if [[ $# -eq 0 ]] ; then
	echo "You need to add 1 arg with foldername"
	exit 1
fi

source ./jmbag.sh

file_name="$1-$JMBAG"
rm $file_name.zip

mkdir $file_name
cp -r $1/src $1/pom.xml $1/webroot $1/config $file_name

zip -r $file_name.zip $file_name
rm -rf $file_name
