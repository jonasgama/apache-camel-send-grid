cls
git add .

git commit -m "Fully integrated with local redis"


echo Enviando dados para o bitbucket
git config user.name "Jonas Gama"
git config user.email "jonasgamaifs@gmail.com"

git push git@github.com:jonasgama/apche-camel-batch-processing.git
echo "Pronto."
@PAUSE