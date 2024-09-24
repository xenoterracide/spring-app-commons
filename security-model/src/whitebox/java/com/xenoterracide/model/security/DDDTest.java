// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.jmolecules.archunit.JMoleculesDddRules;

@AnalyzeClasses
public class DDDTest {

  @ArchTest
  ArchRule valueObjectsDontReferenceEntities = JMoleculesDddRules.valueObjectsMustNotReferToIdentifiables();

  @ArchTest
  ArchRule identifiersPresent = JMoleculesDddRules.annotatedEntitiesAndAggregatesNeedToHaveAnIdentifier();

  @ArchTest
  ArchRule entitiesInAggregate = JMoleculesDddRules.entitiesShouldBeDeclaredForUseInSameAggregate();
}
