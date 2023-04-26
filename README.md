# Trees library.
> An open source library written in Kotlin to work with data structures such as AVL tree, red-black tree, and binary search tree..
## 🖍 Used technology
![Kotlin](https://img.shields.io/badge/-Kotlin-61DAFB?logo=kotlin)
![Junit](https://img.shields.io/badge/Tests-Junit-green)
![Postgresql](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-316192?style=for-the-badge&logo=Docker&logoColor=white)
## :package: Getting started
To build the library run

```sh
  ./gradlew build
```
To run postgresql with docker:
```
./run-db.sh
```
or
```
./run-db.bat
```

## Using Binary search tree
Any `Comparable` data can be stored in trees.
Example:

```kotlin
    import bst.BSTree
    val testData = BSTree(121, "dgs")
    testData.insert(110, "dafad")
    testData.insert(118, "adfaf")
    testData.insert(124, "fggsg")
```
BSTree constructor gets two params: key and value.
insert method also gets same params and add node with key and value in tree.
Method setName allows you to set name of tree, as argument it takes the string.

Find or remove element from tree:
```kotlin    
    testData.remove(124)
    testData.find(118)
```
As a parameter to the find and remove methods take a key-value node

Same operations support red black tree and AVL tree.


AVL tree supports conservation tree object to json file, and receiving tree from json.
For example:
```kotlin
    val test = AVLTree(1231, "afea")
    test.insert(2123, "adf")
    test.insert(2123, "adf")
    test.setName("test_1")
    val control = JsonController()
    control.saveTreeToJson(test)
    println(control.readFromJson("test_1")?.treeName)
```

Also you can save binary search tree to sql database:
```
    val test_data = BSTree(121, "dgs")
    test_data.insert(110, "dafad")
    test_data.insert(118, "adfaf")
    test_data.insert(124, "fggsg")
    test_data.setName("afefadsf")
    val controller = SQLController()
    controller.saveTreeToDB(test_data)
    val remTree = controller.getTree("afefadsf")
```
And you can save red black tree to neo4j database
```
```