#!/bin/bash

# rwx=owner ---=group owner/others
umask 077

# Variables
GPG_OPTS="--quiet --yes --batch"
STORE_DIR="${HOME}/.password_STORE"

# abort func
abort() {
	printf '%s\n' "${1}" 1>&2
	exit 1
}

# encrypt func
gpg() {
	gpg2 $GPG_OPTS --default-recipient-self "$@"
}

# password reader
readpw() {
	# check user interaction, no daemons or programs calling this app
	if [ -t 0 ]; then
		echo -n "Enter password for ${entry_name}:"
		read -s password
	fi
}

# COMMANDS
# insert
insert() {
	entry_name="${1}"
	entry_path="${STORE_DIR}/${entry_name}.gpg"

	if [ -z "${entry_path}" ]; then
		abort "USAGE: PassManager.sh insert PROFILENAME"
	fi

	if [ -e "${entry_path}" ]; then
		abort "This entry or password profile already exists!"
	fi

	# password in $password
	readpw

	if [ -t 0 ]; then
		printf '\n'
	fi

	if [ -z ${password} ]; then
		abort "You did not specify a password"
	fi

	mkdir -p "${entry_path%/*}"
	printf '%s\n' "${password}" | gpg --encrypt --output "${entry_path}"

}

# delete
delete() {
	entry_name="${1}"
	entry_path="${STORE_DIR}/${entry_name}.gpg"

	if [ -z "${entry_path}" ]; then
		abort "USAGE: PassManager.sh insert PROFILENAME"
	fi

	if [ ! -e "${entry_path}" ]; then
		abort "This entry or password profile does not exists!"
	fi

	# password in $password
	readpw

	if [ -t 0 ]; then
		printf '\n'
	fi

	if [ -z ${password} ]; then
		abort "You did not specify a password"
	fi

	rm -r "${entry_path}"
	# printf '%s\n' "${password}" | gpg --encrypt --output "${entry_path}"

}

# show func
show() {
	entry_name="${1}"
	entry_path="$STORE_DIR/${entry_name}.gpg"

	if [ -z "${entry_name}" ]; then
		abort "USAGE: PassManager.sh show PROFILENAME"
	fi

	if [ ! -e "${entry_path}" ]; then
		abort "The requested password profile does not exists!"
	fi

	gpg --decrypt "${entry_path}"

}

# list func
list() {
	for line in $(ls ~/.password_STORE | rev | cut -c 5- | rev); do echo $line; done
}

# MAIN PROGRAMM
if [ "$#" -gt 2 ]; then
	abort "PassManager.sh will not work with more than two arguments!"
fi

if [ "$#" -lt 1 ]; then
	abort "USAGE: PassManager.sh COMMAND PROFILENAME"
fi

case "${1}" in
	"show") show "${2}" ;;
	"list") list ;;
	"insert") insert "${2}" ;;
	"delete") delete "${2}" ;;
	*) abort "USAGE: PassManager.sh COMMAND PROFILENAME" ;;
esac	
