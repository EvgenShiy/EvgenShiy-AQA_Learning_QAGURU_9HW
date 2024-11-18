# Тестирование файлов и разбор JSON

## Задание

1. **Работа с архивом:**
   - Создать ZIP архив с файлами:
     - PDF (`sample.pdf`)
     - Excel (`example.xlsx`)
     - CSV (`example.csv`)
   - Реализовать чтение и проверку содержимого каждого файла из архива.

2. **Работа с JSON:**
   - Использовать библиотеку Jackson для разбора JSON.
   - Пример JSON с объектами `Library` и массивом `Book`.

      
     
  ```json
{
  "libraryName": "City Library",
  "location": "Downtown",
  "books": [
    {
      "title": "The Great Gatsby",
      "author": "F. Scott Fitzgerald",
      "publishedYear": 1925
    },
    {
      "title": "1984",
      "author": "George Orwell",
      "publishedYear": 1949
    }
  ]
}

