package com.buttercat.fridgebook.model;

/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * NOTE: The original file was modified
 */

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
public class AppExecutors {
    /**
     * {@link Executor} for disk operations
     */
    private final Executor mDiskIO;

    /**
     * {@link java.util.concurrent.ExecutorService} for network operations
     */
    private final ExecutorService mNetworkIO;

    /**
     * Private constructor initializing the {@link Executor}
     *
     * @param diskIO an {@link Executor} for disk operations
     */
    private AppExecutors(Executor diskIO, ExecutorService netIO) {
        this.mDiskIO = diskIO;
        this.mNetworkIO = netIO;
    }

    /**
     * Constructor which creates single thread {@link Executor} instances for
     * disk operations and network operations
     */
    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor());
    }

    /**
     * Getter for the disk operations {@link Executor}
     *
     * @return disk operations {@link Executor}
     */
    public Executor diskIO() {
        return mDiskIO;
    }

    /**
     * Getter for the network operations {@link ExecutorService}
     *
     * @return network operations {@link ExecutorService}
     */
    public ExecutorService getNetworkIO() {
        return mNetworkIO;
    }
}