cls
git add .

git commit -m "Kafka Config and readme"


echo Enviando dados para o bitbucket
git config user.name "Jonas Gama"
git config user.email "jonasgamaifs@gmail.com"

git push git@github.com:jonasgama/apache-camel-send-grid.git
echo "Pronto."
@PAUSE