package fr.christopheml.aoc.common.extractors

class MapExtractor<K, V>(
  private val separator: String,
  private val keyTransformer: (String) -> K,
  private val valueTransformer: (String) -> V
): Extractor<Map<K, V>> {
  override fun extract(input: Collection<String>): Map<K, V> =
    input.associate {
      val split = it.split(separator)
      keyTransformer(split[0]) to valueTransformer(split[1])
    }
}

fun mapExtractor(separator: String) = MapExtractor(separator, { it }, { it })
fun <V> mapExtractor(separator: String, valueTransformer: (String) -> V) = MapExtractor(separator, { it }, valueTransformer)
