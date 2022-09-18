sudo apt install make -y
sudo apt install flex -y
sudo apt install bison -y
sudo apt install libx11-dev -y
sudo apt-get install lxc -y
sudo apt install -y libfreetype-dev
./configure --enable-win64
make

grep -r "please"  /home/lql/Downloads/wine-5.0.3/programs/ 
