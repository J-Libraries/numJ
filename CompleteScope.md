Below is an **expanded and detailed breakdown** of all the features and methods in NumPy, organized into broader categories for clarity. I’ll ensure that no important functionality is missed.

---

### 1. **Array Creation**
NumPy provides various functions to create arrays, ranging from simple arrays to more complex patterns and shapes.

#### Basic Array Creation:
- `numpy.array(object, dtype=None)`: Converts input to an ndarray.
- `numpy.zeros(shape, dtype=float)`: Create an array filled with zeros.
- `numpy.ones(shape, dtype=float)`: Create an array filled with ones.
- `numpy.empty(shape, dtype=float)`: Create an uninitialized array (filled with random data initially).

#### Arrays from Sequences:
- `numpy.arange([start,] stop, [step,], dtype=None)`: Return evenly spaced values within a given interval.
- `numpy.linspace(start, stop, num=50, endpoint=True, retstep=False)`: Return evenly spaced numbers over a specified interval.

#### Identity and Diagonal Arrays:
- `numpy.eye(N, M=None, k=0, dtype=float)`: Return a 2-D array with ones on the diagonal and zeros elsewhere.
- `numpy.identity(n, dtype=float)`: Return the identity array.

#### Special Arrays:
- `numpy.full(shape, fill_value, dtype=None)`: Return a new array of given shape and type, filled with `fill_value`.
- `numpy.full_like(a, fill_value, dtype=None)`: Return a full array with the same shape and type as a given array.

#### Random Arrays (random sampling module):
- `numpy.random.rand(d0, d1, ..., dn)`: Generate random values in a given shape (uniform distribution between 0 and 1).
- `numpy.random.randn(d0, d1, ..., dn)`: Generate random numbers from the standard normal distribution.
- `numpy.random.randint(low, high=None, size=None)`: Return random integers from a specified range.

---

### 2. **Array Attributes**
Each ndarray object has a number of attributes that provide information about the array's structure and data.

- `ndarray.shape`: Shape (dimensions) of the array.
- `ndarray.ndim`: Number of dimensions (axes) of the array.
- `ndarray.size`: Total number of elements in the array.
- `ndarray.dtype`: Data type of the array elements.
- `ndarray.itemsize`: Size in bytes of each element in the array.
- `ndarray.nbytes`: Total number of bytes consumed by the elements of the array.
- `ndarray.T`: Transpose of the array (equivalent to `ndarray.transpose()`).

---

### 3. **Array Indexing, Slicing, and Iteration**
Accessing and modifying arrays using indices, slices, and loops.

#### Indexing:
- Standard indexing: `a[i]`, `a[i, j]`
- Boolean indexing: `a[a > 0]`
- Fancy indexing: Use arrays of indices to access multiple array elements.

#### Slicing:
- `a[start:stop:step]`: Works similarly to Python lists, allowing subsetting.
- `a[::]`: Slice all elements across axes.

#### Iterating over Arrays:
- `numpy.nditer()`: Efficient multi-dimensional iterator over arrays.
- `ndarray.flat`: Return a 1-D iterator over the array.

---

### 4. **Array Manipulation and Reshaping**

#### Changing Array Shape:
- `numpy.reshape(a, newshape)`: Give a new shape to an array without changing its data.
- `numpy.ravel(a)`: Flatten an array into a 1D array.
- `numpy.flatten()`: Returns a copy of the array flattened to 1D.
- `numpy.transpose(a, axes=None)`: Permute the dimensions of an array.
- `numpy.swapaxes(a, axis1, axis2)`: Interchange two axes of an array.

#### Combining and Splitting Arrays:
- `numpy.concatenate((a1, a2, ...), axis=0)`: Join a sequence of arrays along an existing axis.
- `numpy.hstack()`: Stack arrays horizontally (column-wise).
- `numpy.vstack()`: Stack arrays vertically (row-wise).
- `numpy.split(ary, indices_or_sections)`: Split an array into multiple sub-arrays.

---

### 5. **Mathematical Operations on Arrays**

#### Basic Element-wise Arithmetic:
- `numpy.add(a, b)`, `numpy.subtract(a, b)`, `numpy.multiply(a, b)`, `numpy.divide(a, b)`: Basic element-wise operations.
- `numpy.mod(a, b)`: Element-wise modulus.
- `numpy.power(a, b)`: Raise elements of `a` to the power `b`.
- `numpy.exp(a)`, `numpy.log(a)`, `numpy.log10(a)`: Exponential and logarithmic functions.

#### Trigonometric Functions:
- `numpy.sin(a)`, `numpy.cos(a)`, `numpy.tan(a)`: Element-wise trigonometric functions.
- `numpy.arcsin(a)`, `numpy.arccos(a)`, `numpy.arctan(a)`: Inverse trigonometric functions.

#### Statistical Functions:
- `numpy.mean(a)`: Mean of array elements.
- `numpy.median(a)`: Median of array elements.
- `numpy.var(a)`, `numpy.std(a)`: Variance and standard deviation.
- `numpy.min(a)`, `numpy.max(a)`: Minimum and maximum of array elements.

#### Aggregation and Cumulative Functions:
- `numpy.sum(a)`: Sum of array elements.
- `numpy.cumsum(a)`: Cumulative sum of elements.
- `numpy.prod(a)`: Product of array elements.
- `numpy.cumprod(a)`: Cumulative product of elements.

#### Linear Algebra:
- `numpy.dot(a, b)`: Dot product of two arrays.
- `numpy.linalg.inv(a)`: Inverse of a matrix.
- `numpy.linalg.det(a)`: Determinant of a matrix.
- `numpy.linalg.eig(a)`: Eigenvalues and eigenvectors.
- `numpy.linalg.norm(a)`: Matrix or vector norm.

---

### 6. **Random Number Generation**
(Part of `numpy.random`)

- `numpy.random.seed(seed)`: Seed the random number generator.
- `numpy.random.rand(d0, d1, ..., dn)`: Random values from a uniform distribution.
- `numpy.random.normal(loc=0.0, scale=1.0, size=None)`: Draw samples from a normal distribution.
- `numpy.random.randint(low, high=None, size=None, dtype='l')`: Random integers.

---

### 7. **Broadcasting**
Broadcasting allows arrays with different shapes to be used together in arithmetic operations.

- **Automatic Broadcasting**: NumPy automatically broadcasts smaller arrays to match the shape of larger arrays during operations like addition or multiplication.

---

### 8. **Sorting and Searching**

#### Sorting:
- `numpy.sort(a, axis=-1)`: Sort an array along a given axis.
- `numpy.argsort(a, axis=-1)`: Indices that would sort the array.
- `numpy.lexsort()`: Indirect stable sort on multiple keys.

#### Searching:
- `numpy.where(condition[, x, y])`: Return elements from `x` or `y` depending on condition.
- `numpy.nonzero(a)`: Return indices of non-zero elements.
- `numpy.argmax(a)`, `numpy.argmin(a)`: Indices of the maximum and minimum values.
- `numpy.searchsorted(a, v)`: Find indices where elements should be inserted to maintain order.

---

### 9. **Set Operations**
(Part of `numpy.lib`)

- `numpy.unique(a)`: Find unique elements in an array.
- `numpy.intersect1d(ar1, ar2)`: Find intersection of two arrays.
- `numpy.union1d(ar1, ar2)`: Find union of two arrays.
- `numpy.setdiff1d(ar1, ar2)`: Set difference of two arrays.
- `numpy.in1d(ar1, ar2)`: Test if elements of one array are in another.

---

### 10. **Handling Missing Data**

- `numpy.isnan(a)`: Return a boolean array indicating whether values are `NaN`.
- `numpy.isfinite(a)`: Return a boolean array indicating whether values are finite.
- `numpy.nan_to_num(a)`: Replace NaN with zero and infinity with large finite numbers.

---

### 11. **Input/Output**

#### Saving and Loading Arrays:
- `numpy.save(file, arr)`: Save an array to a binary file in `.npy` format.
- `numpy.load(file)`: Load an array from a binary `.npy` file.
- `numpy.savetxt(fname, X)`: Save an array to a text file.
- `numpy.loadtxt(fname)`: Load data from a text file.

---

### 12. **Bitwise Operations**

- `numpy.bitwise_and(x1, x2)`, `numpy.bitwise_or(x1, x2)`, `numpy.bitwise_xor(x1, x2)`: Bitwise operations on integers.
- `numpy.invert(x)`: Bitwise NOT of an array.
- `numpy.left_shift(x1, x2)`, `numpy.right_shift(x1, x2)`: Bitwise left or right shift.

---

### 13

. **Data Types and Casting**

- `numpy.astype(dtype)`: Cast array to a specified data type.
- `numpy.dtype()`: Get or specify the data type of an array.
- `numpy.can_cast(from_, to)`: Check if one data type can be cast to another.
- `numpy.iscomplex()`, `numpy.isreal()`: Check if array elements are complex or real.

---

### 14. **Memory Management and Performance**

- `ndarray.flags`: Information about the memory layout of the array.
- `numpy.copy(a)`: Create a copy of the array.
- `numpy.memmap()`: Create a memory-mapped array for large datasets.

---
