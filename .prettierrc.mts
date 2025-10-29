// SPDX-FileCopyrightText: Copyright © 2025 Caleb Cushing
//
// SPDX-License-Identifier: CC0-1.0

// @ts-expect-error: prettier-plugin-toml does not provide ESModule-compatible types or default export; imported as CommonJS
import toml from "prettier-plugin-toml";
// @ts-expect-error: prettier-plugin-sh does not provide ESModule-compatible types or default export; imported as CommonJS
import * as sh from "prettier-plugin-sh";
import xml from "@prettier/plugin-xml";
import * as properties from "prettier-plugin-properties";
import java from "prettier-plugin-java";

export default {
  printWidth: 120,
  xmlWhitespaceSensitivity: "ignore",
  plugins: [xml, properties, java, toml, sh],
};
