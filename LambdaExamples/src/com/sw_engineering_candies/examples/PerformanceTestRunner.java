/*
 * Copyright (C) 2016, Markus Sprunck <sprunck.markus@gmail.com>
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * - The name of its contributor may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.sw_engineering_candies.examples;

import java.util.ArrayList;
import java.util.List;

public class PerformanceTestRunner {

    private static final int EXECUTION_CYCLES = 100;

    private static final int WARM_UP_CYCLES = 200;

    private static final List<PerformanceTestCase> tests = new ArrayList<>();

    public static void add(String description, PerformanceTestLambda block) {
        tests.add(new PerformanceTestCase(block, description));
    }

    public static void executeAll() {

        System.out.println();
        tests.forEach(PerformanceTestRunner::initialize);

        System.out.println();
        tests.forEach(PerformanceTestRunner::execute);
    }

    private static void execute(PerformanceTestCase testCase) {
        long start = System.currentTimeMillis();
        for (int count = 0; count < EXECUTION_CYCLES; count++) {
            testCase.getLambda().execute();
        }
        double duration = ((double) (System.currentTimeMillis() - start) / EXECUTION_CYCLES);

        System.out.println("test case '" + testCase.getComment() + "' elapsed time per execution " + duration + " ms");

    }

    private static void initialize(PerformanceTestCase testcase) {
        System.out.print("test case '" + testcase.getComment() + "' warm-up ");

        for (int count = 0; count < WARM_UP_CYCLES; count++) {
            testcase.getLambda().execute();
            if (count % (WARM_UP_CYCLES / Math.min(20, WARM_UP_CYCLES)) == 0) {
                System.out.print(".");
            }
        }
        System.out.println(" ready");
    }

}