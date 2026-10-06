# Custom Set
Implementation of a customset using an array

All methods implemented are identical to those found in the Java [customset](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) interface.

# Builder and Test

1. To build and test the project run command `./gradlew clean build`
2. To test the project run command `gradle test --tests customset.CustomSetTest`

## Time Complexity

| Method                    |     V1     |    JDK     | Winner |
|---------------------------|:----------:|:----------:|:------:|
| `add(E)`                  |   $O(n)$   |   $O(n)$   |  Tie   |
| `addAll(Collection)`      | $O(n * m)$ | $O(n * m)$ |  Tie   |
| `clear()`                 |   $O(1)$   |   $O(1)$   |  Tie   |
| `contains(E)`             |   $O(n)$   |   $O(n)$   |  Tie   |
| `containsAll(Collection)` | $O(n * m)$ | $O(n * m)$ |  Tie   |
| `isEmpty()`               |   $O(1)$   |   $O(1)$   |  Tie   |
| `remove(E)`               |   $O(n)$   |   $O(n)$   |  Tie   |
| `removeAll(Collection)`   | $O(n * m)$ | $O(n * m)$ |  Tie   |
| `retainAll(Collection)`   | $O(n * m)$ | $O(n * m)$ |  Tie   |
| `size()`                  |   $O(1)$   |   $O(1)$   |  Tie   |
| `toArray()`               |   $O(n)$   |   $O(n)$   |  Tie   |
| `toString()`              |   $O(n)$   |   $O(n)$   |  Tie   |

## Space Complexity

| Method                    |   V1   |  JDK   | Winner |
|---------------------------|:------:|:------:|:------:|
| `add(E)`                  | $O(1)$ | $O(1)$ |  Tie   |
| `addAll(Collection)`      | $O(m)$ | $O(m)$ |  Tie   |
| `clear()`                 | $O(1)$ | $O(1)$ |  Tie   |
| `contains(E)`             | $O(1)$ | $O(1)$ |  Tie   |
| `containsAll(Collection)` | $O(1)$ | $O(1)$ |  Tie   |
| `isEmpty()`               | $O(1)$ | $O(1)$ |  Tie   |
| `remove(E)`               | $O(1)$ | $O(1)$ |  Tie   |
| `removeAll(Collection)`   | $O(1)$ | $O(1)$ |  Tie   |
| `retainAll(Collection)`   | $O(1)$ | $O(1)$ |  Tie   |
| `size()`                  | $O(1)$ | $O(1)$ |  Tie   |
| `toArray()`               | $O(n)$ | $O(n)$ |  Tie   |
| `toString()`              | $O(n)$ | $O(n)$ |  Tie   |

- `n`: Number of elements in the Set.
- `m`: Number of elements in the input collection.