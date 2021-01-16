cd /apps
rm -Rf /apps/build
tar -xvf build.tar.gz
cd /apps/www
ls | grep '.jpg\|.png\|.ttf\|.js\|.jpeg|.jpg|.ttf\|.html\|.tar.gz\|.ico' | xargs rm -f
cp -r /apps/build/* /apps/www
ls /apps/www -l
