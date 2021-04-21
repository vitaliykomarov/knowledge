#!/bin/bash
Recipient="root"
Subject="Greeting"
Message="Welcome"
`mail -s $Subject $Recipient <<< $Message`
