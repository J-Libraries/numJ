package com.library.numj;

import com.library.numj.exceptions.ShapeException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class providing helper methods for array operations in NumJ.
 *
 * @param <T> the type parameter.
 */
public final class Utils<T> {

    /** Map containing the size in bytes of various numeric classes. */
    static Map<Class<?>, Integer> classSizeMap = new HashMap<>();
    static {
        classSizeMap.put(int.class, 4);
        classSizeMap.put(long.class, 8);
        classSizeMap.put(double.class, 8);
        classSizeMap.put(float.class, 4);
        classSizeMap.put(short.class, 2);
        classSizeMap.put(byte.class, 1);
    }

    /**
     * Computes the broadcasted shape from two input shapes.
     *
     * @param shape1 the first shape as a list of integers.
     * @param shape2 the second shape as a list of integers.
     * @return the broadcasted shape as an array of integers.
     * @throws ShapeException if the shapes cannot be broadcast together.
     */
    public int[] broadcastShapes(List<Integer> shape1, List<Integer> shape2) throws ShapeException {

        int len1 = shape1.size();
        int len2 = shape2.size();
        int maxLen = Math.max(len1, len2);

        int[] resultShape = new int[maxLen];
        for (int i = 0; i < maxLen; i++) {
            int dim1 = (i < len1) ? shape1.get(len1 - i - 1) : 1;
            int dim2 = (i < len2) ? shape2.get(len2 - i - 1) : 1;

            if (dim1 == dim2 || dim1 == 1 || dim2 == 1) {
                resultShape[maxLen - i - 1] = Math.max(dim1, dim2);
            } else {
                throw new ShapeException("Shapes cannot be broadcast together: " + shape1 + " and " + shape2);
            }
        }

        return resultShape;
    }

    /**
     * Returns the size in bytes of the given class type.
     *
     * @param clazz the class whose size is to be determined.
     * @return the size in bytes.
     * @throws IllegalArgumentException if the class type is unsupported.
     */
    public int getElementSize(Class<?> clazz) {
        if (classSizeMap.containsKey(clazz)) {
            return classSizeMap.get(clazz);
        } else {
            throw new IllegalArgumentException("Unsupported data type: " + clazz.getSimpleName());
        }
    }

    /**
     * Converts a flat index into multi-dimensional indices based on the given shape.
     *
     * @param flatIndex the flat index.
     * @param shape the shape of the array.
     * @return an array of indices corresponding to each dimension.
     */
    public int[] getMultiDimIndices(int flatIndex, int[] shape) {
        int ndim = shape.length;
        int[] indices = new int[ndim];
        for (int i = ndim - 1; i >= 0; i--) {
            indices[i] = flatIndex % shape[i];
            flatIndex /= shape[i];
        }
        return indices;
    }

    /**
     * Converts multi-dimensional indices to a flat index using the given strides.
     *
     * @param indices the indices in each dimension.
     * @param strides the strides for each dimension.
     * @return the flat index.
     */
    public int getFlatIndex(int[] indices, int[] strides) {
        int flatIndex = 0;
        for (int i = 0; i < indices.length; i++) {
            flatIndex += indices[i] * strides[i];
        }
        return flatIndex;
    }
    public Class<?> getComponentType(T array)
    {
        Class<?> componenetType = array.getClass().getComponentType();
        while (componenetType.isArray())
        {
            componenetType = componenetType.getComponentType();
        }
        return componenetType;
    }
}
