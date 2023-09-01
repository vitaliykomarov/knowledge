# Import required modules
Import-Module ActiveDirectory

# Create a new password
$securePassword = ConvertTo-SecureString "TESTpassw0rd!" -AsPlainText -Force

# Promt user for CSV file path
$filepath = Read-Host -Prompt "Please enter the path to your CSV file"

# Import the file into a variable
$users = Import-Csv $filepath

# Loop through each row and gather information
ForEach ($user in $users) {
    
    # Gather the user's information
    $fname = $user.'First Name'
    $lname = $user.'Last Name'
    $jtitle = $user.'Job Title'
    $officephone = $user.'Office Phone'
    $emailaddress = $user.'Email Address'
    $description = $user.Description
    $OUpath = $user.'Organizational Unit'

    # Create new AD user for each user in CSV file
    New-ADUser -Name "$fname $lname" -GivenName $fname -Surname $lname -UserPrincipalName "$fname.$lname" -Path $OUpath -AccountPassword $securePassword -ChangePasswordAtLogon $true -OfficePhone $officephone -Description $description -Enabled $true -EmailAddress $emailaddress

    # Echo output for each new user
    echo "Account created for $fname $lname in $OUpath"
}