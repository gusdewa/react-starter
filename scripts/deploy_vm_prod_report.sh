cd /apps
echo 'Removing the previous build files...'
rm -Rf /apps/build
echo 'Extracting the zip file...'
tar -xvf /apps/build-report.tar.gz
echo 'Removing the previous web files...'
rm -Rf /apps/www/report
mkdir -p /apps/www/report
echo 'Replacing with the current web files...'
cp -r /apps/build/* /apps/www/report
echo 'Please check the created time below:'
ls /apps/www/report -l
