package com.home.utils.operators

import com.home.utils.elements.latest.Matrix2D

infix fun<T> Matrix2D<T>.strass(b: Matrix2D<T>): Matrix2D<T> {
    val n = this.rows
    val m = this.cols
    val R = this.matrix(n, m)
    /** base case  */
    if (n == 1) {
//        R[0][0] = this[0][0] * b[0][0]
    } else {
        val halfN = n / 2
        val a11 = this.matrix(halfN)
        val a12 = this.matrix(halfN)
        val a21 = this.matrix(halfN)
        val a22 = this.matrix(halfN)
        val b11 = this.matrix(halfN)
        val b12 = this.matrix(halfN)
        val b21 = this.matrix(halfN)
        val b22 = this.matrix(halfN)

        /** Dividing matrix a into 4 halves  */
        split(this, a11, 0, 0)
        split(this, a12, 0, halfN)
        split(this, a21, halfN, 0)
        split(this, a22, halfN, halfN)
        /** Dividing matrix b into 4 halves  */
        split(b, b11, 0, 0)
        split(b, b12, 0, halfN)
        split(b, b21, halfN, 0)
        split(b, b22, halfN, halfN)

        /**
         * m1 = (a11 + a22)(b11 + b22)
         * m2 = (a21 + a22) b11
         * m3 = a11 (b12 - b22)
         * m4 = a22 (b21 - b11)
         * m5 = (a11 + a12) b22
         * m6 = (a21 - a11) (b11 + b12)
         * m7 = (a12 - a22) (b21 + b22)
         */

        val m1 = (a11 + a22) strass (b11 + b22)
        val m2 = (a21 + a22) strass b11
        val m3 = a11 strass (b12 - b22)
        val m4 = a22 strass (b21 - b11)
        val m5 = (a11 + a12) strass b22
        val m6 = (a21 - a11) strass (b11 + b12)
        val m7 = (a12 - a22) strass (b21 + b22)

        /**
         * c11 = m1 + m4 - m5 + m7
         * c12 = m3 + m5
         * c21 = m2 + m4
         * c22 = m1 - m2 + m3 + m6
         */
        val c11 = m1 + m4 - m5 + m7
        val c12 = m3 + m5
        val c21 = m2 + m4
        val c22 = m1 + m3 - m2 + m6

        /** join 4 halves into one result matrix  */
        join(c11, R, 0, 0)
        join(c12, R, 0, halfN)
        join(c21, R, halfN, 0)
        join(c22, R, halfN, halfN)
    }
    /** return result  */
    return R
}

private fun<T> Matrix2D<T>.matrix(n: Int, m: Int) = this.type.matrix(n, m)
private fun<T> Matrix2D<T>.matrix(size: Int) = this.type.matrix(size)


/** Split parent matrix into child matrices  */
private fun<T> split(P: Matrix2D<T>, C: Matrix2D<T>, iB: Int, jB: Int) {
    var i1 = 0
    var i2 = iB
    while (i1 < C.rows) {
        var j1 = 0
        var j2 = jB
        while (j1 < C.cols) {
            C[i1][j1] = P[i2][j2]
            j1++
            j2++
        }
        i1++
        i2++
    }
}

/** Join child matrices into parent matrix  */
private fun<T> join(C: Matrix2D<T>, P: Matrix2D<T>, iB: Int, jB: Int) {
    var i1 = 0
    var i2 = iB
    while (i1 < C.rows) {
        var j1 = 0
        var j2 = jB
        while (j1 < C.cols) {
            P[i2][j2] = C[i1][j1]
            j1++
            j2++
        }
        i1++
        i2++
    }
}