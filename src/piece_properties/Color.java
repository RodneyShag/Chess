/** \brief Each ChessPiece has 4 Characteristics (Color, Moves, Position, Type ) */
package piece_properties;

/** 
 * \brief
 * Pieces are either WHITE or BLACK. NONE is for an empty tile.
 * @author Rodney Shaghoulian
 */
public enum Color {
	WHITE, ///< white ChessPiece
	BLACK, ///< black ChessPiece
	NONE   ///< Color is NONE for empty tiles on a ChessBoard
}
