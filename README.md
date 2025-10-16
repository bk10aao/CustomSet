# Set
Implementation of a Set using an array

All methods implemented are identical to those found in the Java [Set](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) interface.


## Time Complexity

|         Method          | CustomSet V1 (LinkedList) | 
|:-----------------------:|:-------------------------:|
|         add(E)          |           O(n)            |
|   addAll(Collection)    |         O(n * m)          |
|         clear()         |           O(1)            |
|       contains(E)       |           O(n)            |
| containsAll(Collection) |         O(n * m)          |
|        isEmpty()        |           O(1)            |
|        remove(E)        |           O(n)            |
|  removeAll(Collection)  |         O(n * m)          |
|  retainAll(Collection)  |         O(n * m)          |
|         size()          |           O(1)            |
|        toArray()        |           O(n)            |
|       toString()        |           O(n)            |

## Space Complexity

|         Method          |  CustomSet V1 (LinkedList)  |
|:-----------------------:|:---------------------------:|
|         add(E)          |  O(n) + chaining overhead   |
|   addAll(Collection)    |          O(n + m)           |
|         clear()         |            O(1)             |
|       contains(E)       | O(1) per bucket, O(n) worst |
| containsAll(Collection) |          O(n * m)           |
|        isEmpty()        |            O(1)             |
|        remove(E)        | O(1) per bucket, O(n) worst |
|  removeAll(Collection)  |          O(n * m)           |
|  retainAll(Collection)  |          O(n * m)           |
|         size()          |            O(1)             |
|        toArray()        |            O(n)             |
|       toString()        |            O(n)             |
