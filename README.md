# Binary search trees library
> An open source library written in Kotlin to work with data structures such as AVL tree, red-black tree, and binary search tree.
## 🖍 Used technology
![Kotlin](https://img.shields.io/badge/-Kotlin-61DAFB?logo=kotlin)
![Junit](https://img.shields.io/badge/Tests-Junit-green)
![Neo4j](https://img.shields.io/badge/Neo4j-008CC1?style=for-the-badge&logo=neo4j&logoColor=white)
![Postgresql](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-316192?style=for-the-badge&logo=Docker&logoColor=white)
## :package: Getting started
To build the library run

```sh
./gradlew build
```
To run PostgreSQL with docker:
```sh
./start-db.sh
```
or
```sh
./start-db.bat
```

## Using binary search trees
Any data (provided with `Comparable` key) can be stored in trees.
For example:

```kotlin
    import bst.BSTree
    val tree = BSTree(1, "apple")
    tree.insert(7, "orange")
    tree.insert(28, "Alice")
    tree.insert(4, "Bob")
```
Constructor takes two arguments: `key` and `value`, thus instantiating a root node (you can delete it,
but you cannot create an empty tree).
`insert` method also takes same arguments and adds a node with specified `key` and `value` properties to the tree.
Method `setName` allows you to set the name of a tree.

Find or remove element from tree:
```kotlin
    tree.find(4) // returns "Bob"
    tree.remove(1) 
```
`find` and `remove` methods take some `Comparable` key as an argument.

AVL and red-black trees implement the same methods.

## Storing binary search trees
AVL tree can be saved to and loaded from JSON file.
For example:
```kotlin
    val tree = AVLTree(1, "apple")
    tree.setName("test")
    val controller = JsonController()
    controller.saveTreeToJson(test)
    println(controller.readFromJson("test")?.treeName)
```
You can also save binary search tree to SQL database:
```kotlin
    val tree = BSTree(1, "apple")
    tree.setName("test")
    val controller = SQLController()
    controller.saveTreeToDB(test_data)
    val remTree = controller.getTree("test")
```
And you can save red-black tree to Neo4j database
```kotlin
    val tree = RedBlackTree(1, "apple")
    tree.setName("test")
    val controller = Neoj4Conroller()
    contoller.saveTree(tree)
    val remTree = controller.loadTree("test")
```

An example of interacting with trees through a graphical interface
![Example gif](https://github.com/spbu-coding-2022/trees-3/blob/add-ui/readme_gif/example1.gif)