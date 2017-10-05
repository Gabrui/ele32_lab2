rm -r resultados/7z/*
regex="[^/]*$"
for arquivo in textos/biblia/*
do
  s=${arquivo##*/}
  echo "Arquivo compactado:   $s"
  time 7z a "resultados/7z/$s" "$arquivo"
done

