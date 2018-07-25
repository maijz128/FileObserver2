rm -rf release/ 
rm -rf target/ 
mkdir release/
mkdir target/

#  clean && package
mvn clean package -Dmaven.test.skip=true   #-D spring.profiles.active=prod


#  copy jar to release folder
jar_file=`find ./target/  -name "*.jar"`
cp -v ${jar_file} release/


echo && read -t 100 -p "Press any key to continue... "