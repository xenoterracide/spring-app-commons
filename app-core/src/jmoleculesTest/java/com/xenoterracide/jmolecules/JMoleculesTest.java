// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jmolecules;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.jmolecules.archunit.JMoleculesArchitectureRules;
import org.jmolecules.archunit.JMoleculesDddRules;

@AnalyzeClasses(packages = "com.xenoterracide")
class JMoleculesTest {

  @ArchTest
  ArchRule dddRules = JMoleculesDddRules.all();

  @ArchTest
  ArchRule layering = JMoleculesArchitectureRules.ensureLayering();
}
