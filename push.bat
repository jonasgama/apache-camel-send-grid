cls
git add .

git commit -m "Creating an email process"


echo Enviando dados para o bitbucket
git config user.name "Jonas Gama"
git config user.email "jonasgamaifs@gmail.com"

git push git@github.com:jonasgama/apache-camel-send-grid.git
echo "Pronto."
@PAUSE