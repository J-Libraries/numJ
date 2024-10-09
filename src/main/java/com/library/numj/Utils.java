package com.library.numj;

import com.library.numj.exceptions.ShapeException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Utils<T> {

    static Map<Class<?>, Integer> classSizeMap = new HashMap<>();
    static {
        classSizeMap.put(Integer.class, 4);
        classSizeMap.put(Long.class, 8);
        classSizeMap.put(Double.class, 8);
        classSizeMap.put(Float.class, 4);
        classSizeMap.put(Short.class, 2);
        classSizeMap.put(Byte.class, 1);
    }
    public int[] broadcastShapes(List<Integer> shape1, List<Integer> shape2) throws ShapeException {

        int len1 = shape1.size();
        int len2 = shape2.size();
        int maxLen = Math.max(len1, len2);

        int[] resultShape = new int[maxLen];
        for (int i = 0; i < maxLen; i++) {
            int dim1 = (i < len1) ? shape1.get(len1 - i - 1) : 1;
            int dim2 = (i < len2) ? shape2.get(len2 - i - 1) : 1;

            if (dim1 == dim2 || dim1 == 1 || dim2 == 1) {
                resultShape[maxLen-i-1 ] = Math.max(dim1, dim2);
//                resultShape.add(0, Math.max(dim1, dim2));
            } else {
                throw new ShapeException("Shapes cannot be broadcast together: " + shape1 + " and " + shape2);
            }
        }

        return resultShape;
    }

    public int getElementSize(Class<?> clazz) {
        if (classSizeMap.containsKey(clazz)) {
            return classSizeMap.get(clazz);
        } else {
            throw new IllegalArgumentException("Unsupported data type: " + clazz.getSimpleName());
        }
    }
    public int[] getMultiDimIndices(int flatIndex, int[] shape) {
        int ndim = shape.length;
        int[] indices = new int[ndim];
        for (int i = ndim - 1; i >= 0; i--) {
            indices[i] = flatIndex % shape[i];
            flatIndex /= shape[i];
        }
        return indices;
    }
    public int getFlatIndex(int[] indices, int[] strides) {
        int flatIndex = 0;
        for (int i = 0; i < indices.length; i++) {
            flatIndex += indices[i] * strides[i];
        }
        return flatIndex;
    }
}
