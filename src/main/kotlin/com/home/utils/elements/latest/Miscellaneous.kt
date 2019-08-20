package com.home.utils.elements.latest

import com.home.utils.functions.Prod
import com.home.utils.functions.Sum
import com.home.utils.operators.plus

/**
 * Relate to N-dimensional indexes 1-dimensional index.
 * Formula that makes this calculation looks like next Σ{i=0,D}.(e{i} Π{j=0,i}.N{j})
 * D - matrix dimension
 * e = i,j,k,l,m,n ... = indexes
 * N = 1,I,J,K,L,M ... = 1, {sizes of matrix}.
 * Thus
 * i * 1 + j * I + k * (I*J) + l * (I*J*K) + m * (I*J*K*L) + l * (I*J*K*L*M) + ...
 *
 * @param e indexes.
 * @param sizes sizes of matrix.
 */
val index1D = { sizes: IntArray, e: IntArray ->
    val D  = sizes.size
    val N  = 1 + sizes
    (0 until D).Sum { i -> e[i] * (0..i).Prod(N) }
}
