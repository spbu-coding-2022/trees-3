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

## Using BSTs
Any `Comparable` data can be stored in trees.
```kotlin
import bst.AVLTree
import bst.RedBlackTree
import bst.BSTree
val rbTree = RedBlackTree<Int, String>() // instantiate empty red-black tree
val avlTree = AVLTree<Double, String>() // instantiate empty AVL tree
val simpleTree = BSTree<String, String>() // instantiate empty simple tree
```

Each tree supports 3 basic operations: `insert`, `find`, `remove`.
```kotlin
rbTree.insert(10, "E")
rbTree.insert(20, "B")
rbTree.find(10) // returns "E"
rbTree.find(500) // returns null
rbTree.remove(20)  
rbTree.remove(-100)
```
## Storing BSTs 
`bst` provides 