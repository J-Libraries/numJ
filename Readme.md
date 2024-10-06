# NumJ - A Java Numerical Computing Library

NumJ is a Java library inspired by NumPy, providing support for multi-dimensional arrays and mathematical operations. It aims to bring the functionality of NumPy to Java, enabling efficient numerical computations and array manipulations.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
  - [Gradle](#gradle)
  - [Maven](#maven)
- [Usage](#usage)
  - [NDArray Class](#ndarray-class)
    - [Initialization](#initialization)
    - [Attributes](#attributes)
    - [Methods](#methods)
  - [NumJ Class](#numj-class)
    - [`array` Method](#array-method)
    - [`arange` Method](#arange-method)
- [Supported Data Types](#supported-data-types)
- [Exception Handling](#exception-handling)
- [Examples](#examples)
  - [Flattening an Array](#flattening-an-array)
  - [Transposing an Array](#transposing-an-array)
  - [Reshaping an Array](#reshaping-an-array)
  - [Creating an Array with `arange`](#creating-an-array-with-arange)
- [Contributing](#contributing)
- [License](#license)
- [Contact Information](#contact-information)

## Features

- Multi-dimensional array support (`NDArray`)
- Array reshaping (`reshape`)
- Array flattening (`flatten`)
- Array transposition (`transpose`)
- Array creation functions (`arange`, `array`)
- Support for various numerical data types (`DType`)
- Exception handling for shape mismatches and invalid operations

## Installation

NumJ is available via JitPack and can be included in your project using **Gradle** or **Maven**.

### Gradle

Add the following to your `build.gradle` file:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    implementation 'com.github.J-Libraries:numJ:1.0.0'
}
```

### Maven

Add the following to your `pom.xml` file:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.J-Libraries</groupId>
    <artifactId>numJ</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

### NDArray Class

The `NDArray<T>` class represents an N-dimensional array and provides various methods for array manipulations.

#### Initialization

Since the `NDArray` constructor is package-private, you can create an `NDArray` instance using the `array` method provided by the `NumJ` class.

```java
// For a 2D array of integers
Integer[][] data = {{1, 2, 3}, {4, 5, 6}};
NumJ<Integer> numj = new NumJ<>();
NDArray<Integer> array = numj.array(data);
```

#### Attributes

- `ndim()`: Returns the number of dimensions of the array.
- `shape()`: Returns a list representing the size of the array along each dimension.
- `size()`: Returns the total number of elements in the array.
- `itemSize()`: Returns the size of each element in the array (includes dimensions).

#### Methods

- `flatten()`: Flattens the array into a one-dimensional array.
- `transpose()`: Transposes the array by reversing its dimensions.
- `reshape(int... newShape)`: Reshapes the array to the specified shape.
- `printArray()`: Prints the array in a readable format.

**Method Details:**

##### `flatten()`

Flattens an N-dimensional array into a one-dimensional array.

```java
NDArray<Integer> flatArray = array.flatten();
```

##### `transpose()`

Transposes the array by reversing its dimensions. For a 2D array, it swaps rows and columns.

```java
NDArray<Integer> transposedArray = array.transpose();
```

##### `reshape(int... newShape)`

Reshapes the array to the new specified shape. The total number of elements must remain the same.

```java
NDArray<Integer> reshapedArray = array.reshape(3, 2);
```

**Note:** Throws `ShapeException` if the new shape is incompatible.

### NumJ Class

The `NumJ<T>` class provides utility methods for creating arrays and other numerical operations.

#### `array` Method

Creates an `NDArray` from the provided multi-dimensional array.

**Usage Example:**

```java
NumJ<Integer> numj = new NumJ<>();
Integer[][] data = {{1, 2, 3}, {4, 5, 6}};
NDArray<Integer> array = numj.array(data);
```

**Note:** Since the `NDArray` constructor is package-private, you should use the `array` method to create instances of `NDArray`.

#### `arange` Method

Creates an array with evenly spaced values within a given interval.

**Overloads:**

1. `arange(int end)`: Creates an array from `0` to `end - 1`.
2. `arange(int start, int end)`: Creates an array from `start` to `end - 1`.
3. `arange(int start, int end, DType dType)`: Creates an array with the specified data type.

**Usage Examples:**

```java
// Creates [0, 1, 2, 3, 4]
NDArray<Integer> array1 = numj.arange(5);

// Creates [2, 3, 4, 5, 6]
NDArray<Integer> array2 = numj.arange(2, 7);

// Creates [1.0, 2.0, 3.0] with FLOAT32 data type
NDArray<Float> array3 = numj.arange(1, 4, DType.FLOAT32);
```

## Supported Data Types

The `DType` enum defines the supported numerical data types for arrays:

- `FLOAT32`: 32-bit floating-point (`Float`)
- `FLOAT64`: 64-bit floating-point (`Double`)
- `UINT8`: 8-bit unsigned integer (`Byte`)
- `UINT16`: 16-bit unsigned integer (`Short`)
- `UINT32`: 32-bit unsigned integer (`Integer`)
- `UINT64`: 64-bit unsigned integer (`Long`)

**Example:**

```java
NDArray<Double> doubleArray = numj.arange(0, 10, DType.FLOAT64);
```

## Exception Handling

NumJ provides robust exception handling through the `ShapeException` class for operations involving array shapes and sizes.

**Common Exceptions:**

- `ShapeException`: Thrown when an operation cannot be performed due to incompatible shapes.
- `IllegalArgumentException`: Thrown when invalid arguments are provided (e.g., negative sizes).

**Example:**

```java
try {
    NDArray<Integer> reshapedArray = array.reshape(4, 2); // May throw ShapeException
} catch (ShapeException e) {
    e.printStackTrace();
}
```

## Examples

### Flattening an Array

```java
Integer[][] data = {{1, 2}, {3, 4}};
NumJ<Integer> numj = new NumJ<>();
NDArray<Integer> array = numj.array(data);
NDArray<Integer> flatArray = array.flatten();
flatArray.printArray(); // Output: [1, 2, 3, 4]
```

### Transposing an Array

```java
Integer[][] data = {{1, 2, 3}, {4, 5, 6}};
NumJ<Integer> numj = new NumJ<>();
NDArray<Integer> array = numj.array(data);
NDArray<Integer> transposedArray = array.transpose();
transposedArray.printArray(); // Output: [[1, 4], [2, 5], [3, 6]]
```

### Reshaping an Array

```java
Integer[][] data = {{1, 2, 3}, {4, 5, 6}};
NumJ<Integer> numj = new NumJ<>();
NDArray<Integer> array = numj.array(data);
NDArray<Integer> reshapedArray = array.reshape(3, 2);
reshapedArray.printArray(); // Output: [[1, 2], [3, 4], [5, 6]]
```

### Creating an Array with `arange`

```java
NumJ<Integer> numj = new NumJ<>();

// Create an array from 0 to 9
NDArray<Integer> array = numj.arange(10);
array.printArray(); // Output: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
```

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any improvements or bug fixes.

**Note:** Currently, direct pushes to the repository are restricted. All changes must be submitted via pull requests to ensure code reviews and maintain code quality.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact Information

- **Author:** Nishant Mishar
- **Email:** nishantsir57@gmail.com

---






