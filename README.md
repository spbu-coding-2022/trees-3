# Trees library.
> An open source library written in Kotlin to work with data structures such as AVL tree, red-black tree, and binary search tree..
## 🖍 Used technology
![Kotlin](https://img.shields.io/badge/-Kotlin-61DAFB?logo=kotlin)
![Junit](https://img.shields.io/badge/-Junit-525A162?&style=for-the-badge)
## :package: Getting started
Firstly, you need to clone repository

```sh
https://github.com/spbu-coding-2022/trees-3.git
```
To build the library run

```sh
  ./gradlew build
```
## Using BinarySearchTree
Any `Comparable` data can be stored in trees.
You can start from create bst by:

```kotlin
    import bst.BSTree
    val test_data = BSTree("121", "dgs", "tree_1")
    //also we support add and remove elements from bst. You can do this by:
    test_data.insert("110", "dafad")
    test_data.insert("118", "adfaf")
    test_data.insert("124", "fggsg")
```

find or remove element from tree:
```kotlin    
    test_data.remove("124")
    test_data.find("118")
```
Same operations support RBT and AVL trees.


BinarySearchTree supports save tree object to json file, and up tree from json.
For example:
```kotlin
    test_data.saveTreeToJson()
    readFromJson("tree_1.json")
```

In development saving nodes to databases: neo4j and sql.

To save BST in SQL database we propose you to use models in directory main/db.
for example:
```kotlin
        val treeObj = Tree.new {
            name = "Tree_1"
        }
        val rootNode = Node.new {
                key = "test"
                value = "234"
                tree = treeObj
            }
        treeObj.rootNode = rootNode
```

## Storing BSTs 
`bst` provides 