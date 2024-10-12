### Updated `README.md`

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
    - [Arithmetic Operations](#arithmetic-operations)
- [Supported Data Types](#supported-data-types)
- [Exception Handling](#exception-handling)
- [Examples](#examples)
  - [Flattening an Array](#flattening-an-array)
  - [Transposing an Array](#transposing-an-array)
  - [Reshaping an Array](#reshaping-an-array)
  - [Creating an Array with `arange`](#creating-an-array-with-arange)
  - [Arithmetic Operations on Arrays](#arithmetic-operations-on-arrays)
- [Future Scope](#future-scope)
- [Contributing](#contributing)
- [License](#license)
- [Contact Information](#contact-information)

## Features

- Multi-dimensional array support (`NDArray`)
- Array reshaping (`reshape`)
- Array flattening (`flatten`)
- Array transposition (`transpose`)
- Array creation functions (`arange`, `array`)
- Arithmetic operations on arrays (`add`, `subtract`, `multiply`, `divide`, `modulo`)
- Support for various numerical data types (`DType`)
- Exception handling for shape mismatches and invalid operations
- **New**: Support for dynamic step sizes in `arange` method with customized shapes

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
    implementation 'com.github.J-Libraries:numJ:1.0.1'
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
    <version>1.0.1</version>
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
4. `arange(int start, int end, int skip, int[] shape)`: Creates an array with dynamic steps and specified shape.

**Usage Examples:**

```java
// Creates [0, 1, 2, 3, 4]
NDArray<Integer> array1 = numj.arange(5);

// Creates [2, 3, 4, 5, 6]
NDArray<Integer> array2 = numj.arange(2, 7);

// Creates [1.0, 2.0, 3.0] with FLOAT32 data type
NumJ<Float> numjFloat = new NumJ<>();
NDArray<Float> array3 = numjFloat.arange(1, 4, DType.FLOAT32);

// Creates [0, 2, 4, 6] with a shape of 2x2
int[] shape = {2, 2};
NDArray<Integer> array4 = numj.arange(0, 8, 2, shape);
```

### Arithmetic Operations

The `NumJ` class now supports basic arithmetic operations on arrays: addition, subtraction, multiplication, and division. Arrays must have compatible shapes for broadcasting.

**Supported Operations:**

- `add(arr1, arr2)`: Element-wise addition of two arrays.
- `subtract(arr1, arr2)`: Element-wise subtraction of two arrays.
- `multiply(arr1, arr2)`: Element-wise multiplication of two arrays.
- `divide(arr1, arr2)`: Element-wise division of two arrays.
- `modulo(arr1, arr2)`: Element-wise modulo of two arrays.

**Usage Examples:**

```java
NumJ<Integer> numj = new NumJ<>();
NDArray<Integer> arr1 = numj.arange(5); // [0, 1, 2, 3, 4]
NDArray<Integer> arr2 = numj.arange(5); // [0, 1, 2, 3, 4]

// Addition
NDArray<Integer> sum = numj.add(arr1, arr2);
sum.printArray(); // [0, 2, 4, 6, 8]

// Subtraction
NDArray<Integer> diff = numj.subtract(arr1, arr2);
diff.printArray(); // [0, 0, 0, 0, 0]
```

## Supported Data Types

The `DType` enum defines the supported numerical data types for arrays:

```java
public enum DType {
    FLOAT32,  // 32-bit floating-point (Float)
    FLOAT64,  // 64-bit floating-point (Double)
    INT8,     // 8-bit signed integer (Byte)
    INT16,    // 16-bit signed integer (Short)
    INT32,    // 32-bit signed integer (Integer)
    INT64     // 64-bit signed integer (Long)
}
```

**Example:**

```java
NumJ<Double> numjDouble = new NumJ<>();
NDArray<Double> doubleArray = numjDouble.arange(0, 10, DType.FLOAT64);
```

## Exception Handling

NumJ provides robust exception handling through the `ShapeException` class for operations involving array shapes and sizes.

**Common Exceptions:**

- `ShapeException`: Thrown when an operation cannot be performed due to incompatible shapes.
- `IllegalArgumentException`: Thrown when invalid arguments are provided (e.g., negative sizes or skips).

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

### Arithmetic Operations on Arrays

```java
NumJ<Integer> numj = new NumJ<>();
NDArray<Integer> arr1 = numj.arange(5); // [0, 1, 2, 3, 4]
NDArray<Integer> arr2 = numj.arange(5); // [0, 1, 2, 3, 4]

// Element-wise addition
NDArray<Integer> sum = numj.add(arr1, arr2);
sum.printArray(); // [0, 2, 4, 6, 8]

// Element-wise multiplication
NDArray<Integer> product = numj.multiply(arr1, arr2);
product.printArray(); // [0, 1, 4, 9, 16]
```

## Future Scope

NumJ is continuously evolving. The future scope includes implementing more features from NumPy to enhance the library's capabilities. Below is a detailed list of planned additions:

### 1. Advanced Array Creation Functions

- **Zeros and Ones:**
  - `zeros(shape, dtype)`: Create an array filled with zeros.
  - `ones(shape, dtype)`: Create an array filled with ones.
- **Empty and Full:**
  - `empty(shape, dtype)`: Create an uninitialized array.
  - `full(shape, fill_value, dtype)`: Create an array filled with a specified value.
- **Linspace and Logspace:**
  - `linspace(start, stop, num, endpoint, dtype)`: Create an array of evenly spaced numbers over a specified interval.
  - `logspace(start, stop, num, base, dtype)`: Create an array of numbers spaced evenly on a log scale.

### 2. Array Manipulation and Reshaping

- **Shape Manipulation:**
  - `expand_dims(a, axis)`: Expand the shape of an array.
  - `squeeze(a, axis)`: Remove single-dimensional entries from the shape.
- **Joining Arrays:**
  - `concatenate((a1, a2, ...), axis)`: Join a sequence of arrays along an existing axis.
  - `stack(arrays, axis)`: Join a sequence of arrays along a new axis.
- **Splitting Arrays:**
  - `split(a, indices_or_sections, axis)`: Split an array into multiple sub-arrays.
  - `hsplit(a, indices_or_sections)`: Split an array horizontally.
  - `vsplit(a, indices_or_sections)`: Split an array vertically.

### 3. Mathematical Operations

- **Element-wise Mathematical Functions:**
  - `sin(a)`, `cos(a)`, `tan(a)`: Trigonometric functions.
  - `exp(a)`, `log(a)`, `sqrt(a)`: Exponential, logarithmic, and square root functions.
  - `abs(a)`: Absolute value.
- **Aggregate Functions:**
  - `sum(a, axis)`: Sum of array elements over a given axis.
  - `prod(a, axis)`: Product of array elements over a given axis.
  - `cumsum(a, axis)`: Cumulative sum.
  - `cumprod(a, axis)`: Cumulative product.

### 4. Linear Algebra

- **Matrix Operations:**
  - `dot(a, b)`: Dot product of two arrays.
  - `matmul(a, b)`: Matrix product.
  - `transpose(a)`: Transpose of an array.
- **Decompositions:**
  - `linalg.inv(a)`: Inverse of a matrix.
  - `linalg.det(a)`: Determinant of a matrix.
  - `linalg.eig(a)`: Eigenvalues and eigenvectors.

### 5. Random Number Generation

- **Random Sampling:**
  - `random.rand(d0, d1, ..., dn)`: Random values in a given shape.
  - `random.randn(d0, d1, ..., dn)`: Sample from the standard normal distribution.
  - `random.randint(low, high, size, dtype)`: Random integers from `low` (inclusive) to `high` (exclusive).
- **Random Permutations:**
  - `random.shuffle(a)`: Shuffle the array along the first axis.
  - `random.permutation(a)`: Randomly permute a sequence or return a permuted range.

### 6. Broadcasting and Advanced Indexing

- **Broadcasting Mechanism:**
  - Implement full support for broadcasting rules to allow operations on arrays of different shapes.
- **Advanced Indexing:**
  - Support for integer array indexing and boolean array indexing.

### 7. Statistical Functions

- **Descriptive Statistics:**
  - `mean(a, axis)`: Compute the arithmetic mean.
  - `std(a, axis)`, `var(a, axis)`: Compute the standard deviation and variance.
  - `min(a, axis)`, `max(a, axis)`: Minimum and maximum values.
- **Correlation and Covariance:**
  - `corrcoef(x)`: Return Pearson product-moment correlation coefficients.
  - `cov(m)`: Estimate a covariance matrix.

### 8. Sorting, Searching, and Counting

- **Sorting Algorithms:**
  - `sort(a, axis)`: Return a sorted copy of an array.
  - `argsort(a, axis)`: Indirect sort indices.
- **Searching Algorithms:**
  - `where(condition, x, y)`: Return elements chosen from `x` or `y` depending on `condition`.
  - `nonzero(a)`: Return the indices of non-zero elements.
  - `argmax(a, axis)`, `argmin(a, axis)`: Indices of the maximum and minimum values.

### 9. Input/Output Operations

- **File Reading and Writing:**
  - `loadtxt(fname, dtype, delimiter)`: Load data from a text file.
  - `savetxt(fname, X, fmt, delimiter)`: Save an array to a text file.
  - `save(file, arr)`: Save an array to a binary file in NumPy `.npy` format.
  - `load(file)`: Load arrays from `.npy`, `.npz` files, or pickled files.

### 10. Handling Missing Data

- **NaN and Infinite Values:**
  - `isnan(a)`: Return a boolean array where NaN values are `True`.
  - `isinf(a)`: Test element-wise for positive or negative infinity.
  - `nan_to_num(a)`: Replace NaN with zero and infinity with large finite numbers.

### 11. Bitwise Operations

- **Bitwise Functions:**
  - `bitwise_and(x1, x2)`: Compute bitwise AND.
  - `bitwise_or(x1, x2)`: Compute bitwise OR.
  - `invert(x)`: Compute bitwise NOT.
  - `left_shift(x1, x2)`: Shift bits of `x1` left by `x2` positions.
  - `right_shift(x1, x2)`: Shift bits of `x1` right by `x2` positions.

### 12. Set Operations

- **Unique and Set Functions:**
  - `unique(ar)`: Find the unique elements of an array.
  - `intersect1d(ar1, ar2)`: Intersection of two arrays.
  - `union1d(ar1, ar2)`: Union of two arrays.
  - `setdiff1d(ar1, ar2)`: Set difference of two arrays.

### 13. Memory Management and Performance

- **Memory Views:**
  - Implement views for arrays to share data without copying.
- **Memory Mapping:**
  - `memmap`: Memory-mapped file support for large datasets.

### 14. Data Type Handling

- **Type Conversion and Inspection:**
  - `astype(dtype)`: Copy of the array, cast to a specified type.
  - `can_cast(from_, to)`: Returns `True` if cast between data types can occur without loss.
  - `result_type(*arrays_and_dtypes)`: Returns the type resulting from applying the NumPy type promotion rules.

## Contributing

We welcome contributions to NumJ! Follow the steps below to get started:

### Steps for Contribution:

1. **Fork the Repository**:
- Visit the [NumJ GitHub repository](https://github.com/J-Libraries/NumJ) and fork it to your GitHub account.

2. **Select a Task**:
- Go to the [NumJ Project Board](https://github.com/orgs/J-Libraries/projects/1) to view open tasks or issues.
- Choose an issue or task that interests you. You can pick any task that is unassigned.

3. **Clone Your Fork**:
- Clone your forked repository to your local machine.
   ```bash
   git clone https://github.com/YOUR_USERNAME/NumJ.git
   cd NumJ
   ```

4. **Work on the Task**:
- Create a new branch from `main` for your task:
   ```bash
   git checkout -b feature/task-name
   ```
- Make your changes, commit them, and push to your fork:
   ```bash
   git add .
   git commit -m "Add description of the task you worked on"
   git push origin feature/task-name
   ```

5. **Create a Pull Request**:
- After pushing your changes, go to your repository on GitHub and create a pull request (PR) against the `main` branch of the original NumJ repository.

6. **Review Process**:
- Your PR will be reviewed by maintainers. Please be patient, and address any comments or requested changes.

7. **Merge**:
- Once approved, your PR will be merged into the main repository, and you will be credited for your contribution.

For more details on how to contribute, refer to the [Contributing Guidelines](CONTRIBUTING.md) or reach out to the maintainers.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact Information

- **Author:** Nishant Mishra
- **Email:** nishantsir57@gmail.com

---
