rm -r resultados/gzip/*
regex="[^/]*$"
for arquivo in textos/biblia/*
do
  s=${arquivo##*/}
  echo "Arquivo compactado:   $s"
  time gzip -c "$arquivo" > "resultados/gzip/$s".gzip
done