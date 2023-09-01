# Store users first name into variable
$firstname = Read-Host -Prompt "Please enter your first name"
$lastname = Read-Host -Prompt "Please enter your last name"
$password = "TESTpassw0rd"

# Output the users information
echo "Your full name is $firstname $lastname. Your password is $password"

# Specify where to store the user account
$OUpath = "OU=PowerShellUsers,DC=it,DC=com"

# Convert the password to a secure string
$securePassword = ConvertTo-SecureString $password -AsPlainText -Force

# Create the user account
New-ADUser -Name "$firstname $lastname" -GivenName $firstname -Surname $lastname -UserPrincipalName "$firstname.$lastname" -Path $OUpath -AccountPassword $securePassword -ChangePasswordAtLogon $True -Enabled $True