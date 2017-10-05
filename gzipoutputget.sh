cat gzipoutputget | grep "Arquivo"

for arquivo in resultados/gzip/*
do
  tamanho=$(stat -c%s "$arquivo")
  echo "$arquivo =$tamanho"
done

cat gzipoutputget | grep "real"