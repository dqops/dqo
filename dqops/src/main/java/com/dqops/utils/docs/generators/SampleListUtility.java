/*
 * Copyright © 2023 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dqops.utils.docs.generators;

import com.dqops.utils.docs.DocumentationReflectionService;
import com.dqops.utils.docs.DocumentationReflectionServiceImpl;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import org.apache.commons.lang3.math.NumberUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class SampleListUtility {
    private static DocumentationReflectionService reflectionService = new DocumentationReflectionServiceImpl(new ReflectionServiceImpl());

    public enum HelperMutators {
        ITERATE_STRING {
            public String mutate(String s) {
                if (!NumberUtils.isParsable(s.substring(s.length() - 1))) {
                    return s + "_1";
                }
                int underscoreIndex = s.lastIndexOf('_');
                int i = Integer.parseInt(s, underscoreIndex + 1, s.length(), 10);
                return s.substring(0, underscoreIndex + 1) + (i + 1);
            }

            @Override
            public Function<String, String> getMutator() {
                return this::mutate;
            }
        };

        public abstract Function<?, ?> getMutator();
    }

    protected static <T, D> void modifySingleDimension(List<T> list,
                                                       Function<T, D> getter,
                                                       Function<D, D> mutator,
                                                       BiConsumer<T, D> setter) {
        if (list.isEmpty()) {
            return;
        }
        
        T elem = list.get(0);
        D currentValue = getter.apply(elem);
        for (int i = 1; i < list.size(); ++i) {
            currentValue = mutator.apply(currentValue);
            T newElem = list.get(i);
            setter.accept(newElem, currentValue);
        }
    }

    /**
     * Generate a list of <code>n</code> string samples.
     * @param baseString Base string for ist generation.
     * @param n List size.
     * @return List with generated samples.
     */
    public static List<String> generateStringList(String baseString, int n) {
        List<String> resultList = new ArrayList<>(n);
        Function<String, String> mutator = (Function<String, String>) HelperMutators.ITERATE_STRING.getMutator();
        String currentValue = baseString;
        for (int i = 0; i < n; ++i) {
            currentValue = mutator.apply(currentValue);
            resultList.add(currentValue);
        }
        return resultList;
    }
    
    /**
     * Generate a list of <code>n</code> samples of the given type.
     * @param n List size.
     * @return List with generated samples.
     * @param <T> Type for which the sample will be generated.
     */
    public static <T> List<T> generateList(Type type, int n) {
        // TODO: recursive sample generation engine
        //TypeModel typeModel = reflectionService.getObjectsTypeModel(type, c -> null);
        List<T> resultList = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            resultList.add(GeneratorUtility.generateSample((Class<? extends T>) type));
        }
        return resultList;
    }
    
    /**
     * Generate a list of <code>n</code> samples of the given type.
     * @param n List size.
     * @param dimension1Getter  Accessor to the 1st dimension of object <code>T</code>.
     * @param dimension1Mutator Modifier to the 1st dimension of object <code>T</code>.
     *                          The mutator is applied to the last result of the mutator, e.g. (a) -> a + 1; will result in a chain of subsequent integers.
     * @param dimension1Setter  Setter for the 1st dimenstion of object <code>T</code>.
     * @return List with generated samples.
     * @param <T> Type for which the sample will be generated.
     * @param <D1> 1st dimension of <code>T</code>.
     */
    public static <T, D1> List<T> generateList(Type type, int n,
                                               Function<T, D1> dimension1Getter,
                                               Function<D1, D1> dimension1Mutator,
                                               BiConsumer<T, D1> dimension1Setter) {
        List<T> resultList = generateList(type, n);
        modifySingleDimension(resultList, dimension1Getter, dimension1Mutator, dimension1Setter);
        return resultList;
    }

    public static <T, D1, D2> List<T> generateList(Type type, int n,
                                                   Function<T, D1> dimension1Getter,
                                                   Function<D1, D1> dimension1Mutator,
                                                   BiConsumer<T, D1> dimension1Setter,
                                                   Function<T, D2> dimension2Getter,
                                                   Function<D2, D2> dimension2Mutator,
                                                   BiConsumer<T, D2> dimension2Setter) {
        List<T> resultList = generateList(type, n,
                dimension1Getter, dimension1Mutator, dimension1Setter);
        modifySingleDimension(resultList, dimension2Getter, dimension2Mutator, dimension2Setter);
        return resultList;
    }

    public static <T, D1, D2, D3> List<T> generateList(Type type, int n,
                                                       Function<T, D1> dimension1Getter,
                                                       Function<D1, D1> dimension1Mutator,
                                                       BiConsumer<T, D1> dimension1Setter,
                                                       Function<T, D2> dimension2Getter,
                                                       Function<D2, D2> dimension2Mutator,
                                                       BiConsumer<T, D2> dimension2Setter,
                                                       Function<T, D3> dimension3Getter,
                                                       Function<D3, D3> dimension3Mutator,
                                                       BiConsumer<T, D3> dimension3Setter) {
        List<T> resultList = generateList(type, n,
                dimension1Getter, dimension1Mutator, dimension1Setter,
                dimension2Getter, dimension2Mutator, dimension2Setter);
        modifySingleDimension(resultList, dimension3Getter, dimension3Mutator, dimension3Setter);
        return resultList;
    }
}
