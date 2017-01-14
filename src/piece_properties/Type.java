package piece_properties;

/** 
 * \brief
 * 6 Standard types of ChessPieces, and the new "Horizon" and "Centaur" pieces
 * @author Rodney Shaghoulian
 */
public enum Type {
	PAWN, 			///< Standard ChessPiece: Moves vertically, attacks diagonally.
	ROOK, 			///< Standard ChessPiece: Moves/attacks left, right, up, down
	KNIGHT, 		///< Standard ChessPiece: Has 8 different "jump" moves/attacks
	BISHOP, 		///< Standard ChessPiece: Moves/attacks diagonally NW, NE, SW, SE
	QUEEN, 			///< Standard ChessPiece: Moves/attacks left, right, up, down, NW, NE, SW, SE
	KING, 			///< Standard ChessPiece: Moves/attacks like QUEEN except 1-step at a time
	HORIZON,	 	///< Special ChessPiece:  Moves/attacks only left and right
	CENTAUR,		///< Special ChessPiece:  Moves/attacks like a KNIGHT, except with longer range
	SHAPESHIFTER,	///< Special ChessPiece:  Starts off as QUEEN. After 1st move: turns into a BISHOP, KNIGHT, PAWN, or ROOK of same color
	DECOY			///< Special ChessPiece:  Moves like a QUEEN. At first looks like a QUEEN. After every move, its icon changes.
}
