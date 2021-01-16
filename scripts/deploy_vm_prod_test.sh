mkdir -p /apps/test-internal/build
rm -Rf /apps/test-internal/build
cd /apps/test-internal
tar -xvf build.tar.gz
cd /apps/www/test-internal
ls | grep '.jpg\|.png\|.ttf\|.js\|.jpeg|.jpg|.ttf\|.html\|.tar.gz\|.ico' | xargs rm -f
cp -r /apps/test-internal/build/* /apps/www/test-internal
ls /apps/www/test-internal -l
