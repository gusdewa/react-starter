echo 'Make backup files...'
sudo cp /etc/nginx/conf.d/default.conf /etc/nginx/conf.d/default.conf.bak
sudo cp /etc/nginx/nginx.conf /etc/nginx/nginx.conf.bak
echo 'Replacing Nginx config files...'
sudo cp /apps/default.conf /etc/nginx/conf.d/default.conf
sudo cp /apps/nginx.conf /etc/nginx/nginx.conf
echo 'Restarting Nginx service...'
sudo service nginx restart
echo 'Checking status result...'
sudo service nginx status
echo 'Printing updated config...'
ls -l /etc/nginx/conf.d/
ls -l /etc/nginx/
