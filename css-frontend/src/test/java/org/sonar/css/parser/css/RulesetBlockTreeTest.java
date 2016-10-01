/*
 * SonarQube CSS / Less Plugin
 * Copyright (C) 2013-2016 Tamas Kende and David RACODON
 * mailto: kende.tamas@gmail.com and david.racodon@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.css.parser.css;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.RulesetBlockTree;

import static org.fest.assertions.Assertions.assertThat;

public class RulesetBlockTreeTest extends CssTreeTest {

  public RulesetBlockTreeTest() {
    super(LexicalGrammar.RULESET_BLOCK);
  }

  @Test
  public void rulesetBlock() {
    RulesetBlockTree tree;

    tree = checkParsed("{}");
    assertThat(tree.content()).isNull();

    tree = checkParsed(" {}");
    assertThat(tree.content()).isNull();

    tree = checkParsed(" { }");
    assertThat(tree.content()).isNull();

    tree = checkParsed("{color:green}");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();

    tree = checkParsed(" { color : green }");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();

    tree = checkParsed(" { \ncolor : green\n }");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();

    tree = checkParsed(" { \ncolor : green;\ncolor: red;\n--myvar: blabla }");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).hasSize(1);
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();

    tree = checkParsed(" { \ncolor : green;\ncolor: red;\n--myvar: blabla; }");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).hasSize(1);
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();

    tree = checkParsed(" { \ncolor : green;\ncolor: red;\n--myvar: blabla;}");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).hasSize(1);
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
  }

  @Test
  public void notRulesetBlock() {
    checkNotParsed("{color:}");
  }

  private RulesetBlockTree checkParsed(String toParse) {
    RulesetBlockTree tree = (RulesetBlockTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.openCurlyBrace()).isNotNull();
    assertThat(tree.closeCurlyBrace()).isNotNull();
    assertThat(tree.variableDeclarations()).isNotNull();
    assertThat(tree.lessVariableDeclarations()).isNotNull();
    assertThat(tree.rulesets()).isNotNull();
    return tree;
  }

}